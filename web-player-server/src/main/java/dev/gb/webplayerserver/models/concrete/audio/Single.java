package dev.gb.webplayerserver.models.concrete.audio;

import dev.gb.webplayerserver.models.concrete.creators.Artist;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "SINGLES")
public class Single extends Track {
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "SINGLE_ARTIST",
            joinColumns = @JoinColumn(name = "SINGLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "ARTIST_ID")
    )
    private final Set<Artist> artistSet = new HashSet<>();

    public void addArtist(Artist artist){
        artistSet.add(artist);
    }

    public void removeArtist(Artist artist){
        artistSet.remove(artist);
    }


    public Set<Artist> getArtistSet() {
        return new HashSet<>(artistSet);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Single)) return false;
        if (!super.equals(o)) return false;

        Single single = (Single) o;

        return Objects.equals(artistSet, single.artistSet) && Objects.equals(this.getId(), single.getId());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + artistSet.hashCode();
        return result;
    }
}
