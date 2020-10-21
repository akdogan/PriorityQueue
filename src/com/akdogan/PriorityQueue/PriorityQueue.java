package com.akdogan.PriorityQueue;

import java.util.ArrayList;
import java.util.TreeMap;

public class PriorityQueue
{
    private ArrayList<Task> tasks;

    private TreeMap<Integer, Integer> map;

    /**
     * Creates a Queue which is always sorted by priority
     */
    public PriorityQueue(){
        this.tasks = new ArrayList<Task>();
        map = new TreeMap<>();


    }

    /**
     * Adds a task to the Queue. Position is determined by the Priority. All tasks will be added after the last
     * item of the same priority, or after the item with the next highest priority, if no other items of this
     * priority have been added before. Priority is determined by an Integer Value, higher values are higher priority.
     * @param t a Task Object, that will be added to the Queue. Must implemente Interface Task.
     */
    public void add(Task t){

        int prio = t.getPriority();
        if (tasks.size() == 0) {
            addInternal(t, 0);
        }
        else if (map.higherKey(prio) == null){
            addInternal(t, 0);
        }
        else if (map.lowerKey(prio) == null){
            addInternal(t, tasks.size());
        }
        else if (map.get(prio) != null){
            int index = map.get(prio) + 1;
            addInternal(t, index);
        }
        else {
            int index = map.get(map.higherKey(prio)) + 1;
            addInternal(t, index);
            moveDown(prio-1);
        }
    }

    private void addInternal(Task t, int index){
        int priority = t.getPriority();
        tasks.add(index, t);
        if (map.containsKey(priority)){
            map.replace(priority, index);
        }
        else {
            map.put(priority, index);
        }
    }

    private void moveDown(int priority){
        for ( int oldPrio : map.keySet()){
            if (oldPrio <= priority){
                map.replace(oldPrio, map.get(oldPrio)+1);
            }
        }
    }


    /**
     * See if there are currently any tasks left in the queue
     * @return true if there is at least one task left in the queue, false if empty
     */
    public boolean hasNext(){
        return !(tasks.size() == 0);
    }

    /**
     * Returns the next task to check its values. This does not remove the next task from the queue and should only
     * be used to inspect the next task
     * @return the next task in the queue
     */
    public Task getNext(){
        return tasks.get(0);
    }

    /**
     * Check if a task with the provided description exists. Does not remove the task from the queue.
     * @param name description of the task to search for
     * @return true if a task with the provided description exists in the queue
     */
    public boolean contains(String name){
        boolean found = false;
        int i = 0;
        while (!found && i <tasks.size()){
            found = tasks.get(i).getDescription().equals(name);
            if (!found) i++;
        }
        return found;
    }

    /**
     * Returns the task at the given index of the queue. Does not remove the task from the queue.
     * @param i the index of the task
     * @return the task at the provided index, or null if the provided index does not exist.
     */
    public Task get(int i){
        if ( tasks.size() > i){
            return tasks.get(i);
        }
        return null;
    }

    /**
     * Returns the next task in the queue. This will REMOVE the task from the queue before returning it.
     * @return the next task in the queue.
     */
    public Task doNext(){
        Task t = tasks.get(0);
        tasks.remove(0);
        if (tasks.size() > 0 && tasks.get(0).getPriority() < t.getPriority()){
            map.remove(t.getPriority());
            map.replace(map.lowerKey(t.getPriority()), 0);
        }
        return t;
    }


    public String toString(){
        String str = "";
        for ( Task t : tasks){
            str += t.getPriority() + ": " + t.getDescription() + "\n";
        }
        return str;
    }


}

