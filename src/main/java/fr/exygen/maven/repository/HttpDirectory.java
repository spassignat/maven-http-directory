package fr.exygen.maven.repository;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.metadata.ArtifactMetadata;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.codehaus.plexus.component.annotations.Component;

@Component(role = ArtifactRepositoryLayout.class, hint = "http-directory")
public class HttpDirectory implements ArtifactRepositoryLayout {
	private static final char PATH_SEPARATOR = '/';
	private static final char GROUP_SEPARATOR = '.';
	private static final char ARTIFACT_SEPARATOR = '-';

	public HttpDirectory() {
	}

	public String getId() {
		return "http-directory";
	}

	public String pathOf(Artifact artifact) {
		ArtifactHandler artifactHandler = artifact.getArtifactHandler();
		StringBuilder path = new StringBuilder(128);
		path.append(this.formatAsDirectory(artifact.getGroupId())).append('/');
		path.append(artifact.getArtifactId()).append('/');
		path.append(artifact.getBaseVersion()).append('/');
		path.append(artifact.getArtifactId()).append('-').append(artifact.getVersion());
		if (artifact.hasClassifier()) {
			path.append('-').append(artifact.getClassifier());
		}
		if (artifactHandler.getExtension() != null && artifactHandler.getExtension().length() > 0) {
			path.append('.').append(artifactHandler.getExtension());
		}
		return path.toString();
	}

	public String pathOfLocalRepositoryMetadata(ArtifactMetadata metadata, ArtifactRepository repository) {
		return this.pathOfRepositoryMetadata(metadata, metadata.getLocalFilename(repository));
	}

	private String pathOfRepositoryMetadata(ArtifactMetadata metadata, String filename) {
		StringBuilder path = new StringBuilder(128);
		path.append(this.formatAsDirectory(metadata.getGroupId())).append('/');
		if (!metadata.storedInGroupDirectory()) {
			path.append(metadata.getArtifactId()).append('/');
			if (metadata.storedInArtifactVersionDirectory()) {
				path.append(metadata.getBaseVersion()).append('/');
			}
		}
		path.append(filename);
		return path.toString();
	}

	public String pathOfRemoteRepositoryMetadata(ArtifactMetadata metadata) {
		return this.pathOfRepositoryMetadata(metadata, metadata.getRemoteFilename());
	}

	private String formatAsDirectory(String directory) {
		return directory.replace('.', '/');
	}

	public String toString() {
		return this.getId();
	}
}
