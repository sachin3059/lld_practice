package models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CashDispenser {
    private final TreeMap<Integer, Integer> notesCount;

    public CashDispenser(){
        this.notesCount = new TreeMap<>(Collections.reverseOrder());
    }

    public void addNote(int demon, int count){
        if(count <= 0){
            throw new IllegalArgumentException("Note count must be greater than 0");
        }
        notesCount.put(demon, notesCount.getOrDefault(demon, 0) + count);
    }

    public Map<Integer, Integer> dispense(int amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        int remainingAmount = amount;
        Map<Integer, Integer> result = new HashMap<>();

        for(Map.Entry<Integer, Integer> entry : notesCount.entrySet()){
            int demon = entry.getKey();
            int count = entry.getValue();

            int need = Math.min(remainingAmount / demon , count);
            remainingAmount -= need * demon;

            result.put(demon, need);

            if(remainingAmount == 0) break;
        }

        if(remainingAmount != 0){
            return Collections.emptyMap();
        }

        for(Map.Entry<Integer, Integer> entry : result.entrySet()){
            int demon = entry.getKey();
            int used = entry.getValue();

            notesCount.put(demon, notesCount.get(demon) -  used);
        }

        return result;
    }
}
