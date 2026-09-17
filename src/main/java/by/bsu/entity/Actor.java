package by.bsu.entity;

import java.time.LocalDate;

public class Actor {
    private final int id;
    private final String fullName;
    private final LocalDate birthday;

    private Actor(Builder builder) {
        this.id = builder.id;
        this.fullName = builder.fullName;
        this.birthday = builder.birthday;
    }

    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public LocalDate getBirthday() { return birthday; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        if (id != actor.id) return false;
        if (fullName != null ? !fullName.equals(actor.fullName) : actor.fullName != null) return false;
        return birthday != null ? birthday.equals(actor.birthday) : actor.birthday == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Actor{" + "id=" + id + ", fullName='" + fullName + '\'' + ", birthday=" + birthday + '}';
    }

    // Builder
    public static class Builder {
        private int id;
        private String fullName;
        private LocalDate birthday;

        public Builder id(int id) { this.id = id; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder birthday(LocalDate birthday) { this.birthday = birthday; return this; }

        public Actor build() { return new Actor(this); }
    }
}