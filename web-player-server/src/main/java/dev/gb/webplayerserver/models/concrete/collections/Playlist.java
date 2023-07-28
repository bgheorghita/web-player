package dev.gb.webplayerserver.models.concrete.collections;

import dev.gb.webplayerserver.models.base.AudioFileCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PLAYLISTS")
public class Playlist extends AudioFileCollection {
//    @ManyToMany
//    @JoinColumn(name = "PLAYLIST_ID")
//    private Set<AudioFile> audioFileSet = new HashSet<>();
//    private LocalDate creationDate;
//
//    public Set<AudioFile> getAudioFileSet() {
//        return new HashSet<>(audioFileSet);
//    }
//
//    public void setAudioFileSet(Set<AudioFile> audioFileSet) {
//        this.audioFileSet = audioFileSet;
//    }
//
//    public LocalDate getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(LocalDate creationDate) {
//        this.creationDate = creationDate;
//    }
}
