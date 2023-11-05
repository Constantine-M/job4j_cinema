package ru.job4j.cinema.model;

import java.util.Objects;

public class File {

    private int id;

    private String name;

    private String path;

    public File(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String path() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        File file = (File) o;
        return id == file.id && Objects.equals(path, file.path);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
