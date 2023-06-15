package com.example.projectem.users;

import java.util.ArrayList;

public class Task {
    String task;
    ArrayList<String> users;
    int points;

    public Task() {
    }

    public Task( int points,String taskask) {
        this.users = new ArrayList<>();
        this.points = points;
        this.task=taskask;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Task{" +
                "users=" + users +
                ", points=" + points +
                '}';
    }
}
