public class CashDispenser {
    private final TreeMap<Integer, Integer> noteCount;

    public CashDispenser() {

        this.noteCount = new TreeMap<>(Collections.reverseOrder());
    }


    public void addNotes(int denomination, int count){
        if(count <= 0){
            throw new IllegalArgumentException("Count must be greater than zero");
        }
        noteCount.put(denomination, noteCount.getOrDefault(denomination, 0) + count);
    }

    public Map<Integer, Integer> dispense(int amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Map<Integer, Integer> result = new HashMap<>();
        int remaining = amount;

        for(Map.Entry<Integer, Integer> entry : noteCount.entrySet()){
            int denom = entry.getKey();
            int available = entry.getValue();

            if(remaining >= denom && available > 0){
                int notesNeeded = Math.min(remaining / denom, available);
                result.put(denom, notesNeeded);
                remaining -= notesNeeded * denom;
            }

            if(remaining == 0) break;
        }

        if(remaining != 0){
            return Collections.emptyMap();
        }

        for(Map.Entry<Integer, Integer> entry : result.entrySet()){
            int denom = entry.getKey();
            int used = entry.getValue();

            noteCount.put(denom, noteCount.get(denom) - used);
        }

        return result;
    }

    public int getTotalCash(){
        int total = 0;
        for(Map.Entry<Integer, Integer> entry : noteCount.entrySet()){
            total += entry.getKey() * entry.getValue();
        }
        return total;
    }
}