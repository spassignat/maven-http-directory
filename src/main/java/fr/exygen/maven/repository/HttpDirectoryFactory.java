package fr.exygen.maven.repository;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.spi.connector.checksum.ChecksumAlgorithmFactory;
import org.eclipse.aether.spi.connector.layout.RepositoryLayout;
import org.eclipse.aether.spi.connector.layout.RepositoryLayoutFactory;
import org.eclipse.aether.transfer.NoRepositoryLayoutException;

import javax.inject.Named;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Named("http-directory")
public final class HttpDirectoryFactory implements RepositoryLayoutFactory {
	static final String CONFIG_PROP_CHECKSUMS_ALGORITHMS = "aether.checksums.algorithms";
	static final String CONFIG_PROP_SIGNATURE_CHECKSUMS = "aether.checksums.forSignature";
	static final String DEFAULT_CHECKSUMS_ALGORITHMS = "SHA-1,MD5";
	private float priority;

	public HttpDirectoryFactory() {
	}

	public float getPriority() {
		return this.priority;
	}

	public HttpDirectoryFactory setPriority(float priority) {
		this.priority = priority;
		return this;
	}

	public RepositoryLayout newInstance(RepositorySystemSession session, RemoteRepository repository) throws NoRepositoryLayoutException {
		if (!"http-directory".equals(repository.getContentType())) {
			throw new NoRepositoryLayoutException(repository);
		} else {
			return new MyRepo();
		}
	}

	class MyRepo implements RepositoryLayout {
		@Override
		public List<ChecksumAlgorithmFactory> getChecksumAlgorithmFactories() {
			return new ArrayList<>();
		}

		@Override
		public List<ChecksumLocation> getChecksumLocations(Artifact artifact, boolean b, URI uri) {
			return new ArrayList<>();
		}

		@Override
		public List<ChecksumLocation> getChecksumLocations(Metadata metadata, boolean b, URI uri) {
			return new ArrayList<>();
		}

		@Override
		public URI getLocation(Artifact artifact, boolean upload) {
			StringBuilder path = new StringBuilder(128);
			path.append(artifact.getGroupId().replace('.', '/')).append('/');
			path.append(artifact.getArtifactId()).append('/');
			path.append(artifact.getBaseVersion()).append('/');
			path.append(artifact.getArtifactId()).append('-').append(artifact.getVersion());
			if (artifact.getClassifier().length() > 0) {
				path.append('-').append(artifact.getClassifier());
			}
			if (artifact.getExtension().length() > 0) {
				path.append('.').append(artifact.getExtension());
			}
			final URI uri = this.toUri(path.toString());
			System.out.println("uri = " + uri);
			return uri;
		}

		public URI getLocation(Metadata metadata, boolean upload) {
			StringBuilder path = new StringBuilder(128);
			if (metadata.getGroupId().length() > 0) {
				path.append(metadata.getGroupId().replace('.', '/')).append('/');
				if (metadata.getArtifactId().length() > 0) {
					path.append(metadata.getArtifactId()).append('/');
					if (metadata.getVersion().length() > 0) {
						path.append(metadata.getVersion()).append('/');
					}
				}
			}
			path.append(metadata.getType());
			final URI uri = this.toUri(path.toString());
			System.out.println("uri = " + uri);
			return uri;
		}

		@Override
		public boolean hasChecksums(Artifact artifact) {
			return false;
		}

		private URI toUri(String path) {
			try {
				return new URI((String) null, (String) null, path, (String) null);
			} catch (URISyntaxException var3) {
				throw new IllegalStateException(var3);
			}
		}
	}
}
