package dev.gb.webplayerserver.models.base;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
public abstract class AudioFile extends BaseEntity {
    private String title;
    private int durationInSeconds;
    private String filePath;
    private String description;
    private LocalDate addedDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AudioFile)) return false;
        if (!super.equals(o)) return false;

        AudioFile audioFile = (AudioFile) o;

        if (durationInSeconds != audioFile.durationInSeconds) return false;
        if (!Objects.equals(title, audioFile.title)) return false;
        if (!Objects.equals(filePath, audioFile.filePath)) return false;
        if (!Objects.equals(description, audioFile.description))
            return false;
        return Objects.equals(addedDate, audioFile.addedDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + durationInSeconds;
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (addedDate != null ? addedDate.hashCode() : 0);
        return result;
    }
}
