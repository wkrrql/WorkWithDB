package by.bsu.entity;

import java.time.LocalDate;

public class Film {
    private final int id;
    private final String name;
    private final LocalDate releaseDate;
    private final String country;

    private Film(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.releaseDate = builder.releaseDate;
        this.country = builder.country;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public String getCountry() { return country; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        if (id != film.id) return false;
        if (name != null ? !name.equals(film.name) : film.name != null) return false;
        if (releaseDate != null ? !releaseDate.equals(film.releaseDate) : film.releaseDate != null) return false;
        return country != null ? country.equals(film.country) : film.country == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Film{id=" + id + ", name='" + name + "', releaseDate=" + releaseDate + ", country='" + country + "'}";
    }

    public static class Builder {
        private int id;
        private String name;
        private LocalDate releaseDate;
        private String country;

        public Builder id(int id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder releaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; return this; }
        public Builder country(String country) { this.country = country; return this; }

        public Film build() { return new Film(this); }
    }
}