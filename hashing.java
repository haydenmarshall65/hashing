import java.io.*;
public class hashing{

    public static void main(String[] args) throws Exception {
        int recordSize = 26;
        String stdRecords[] = new String[10];
        byte record[] = new byte[26];

        RandomAccessFile raf = new RandomAccessFile(new File("records.txt"), "r");

        for(int i = 0; i < raf.length(); i+= recordSize){

            raf.seek(i);
            raf.read(record);
            System.out.println("Inserting record " + new String(record));

            insert(new String(record), stdRecords);
        }

        String hashedRecord = "";
        for(int i = 0; i < raf.length(); i+= recordSize){
            raf.seek(i);
            raf.read(record);
            int key = Integer.parseInt(new String(record).substring(0, 4));

            hashedRecord = retrieve(key, stdRecords);
            System.out.println("Record found: " + hashedRecord);
        }

        raf.close();
    }

    public static int hash(int key, int tableSize){

        int hashedKey = key % tableSize;

        return hashedKey;
    }

    public static String[] insert(String record, String[] table){
        boolean inserted = false;
        int key = Integer.parseInt(record.substring(0, 4));
        
        int location = hash(key, table.length);
        System.out.println("Initial hash location: " + location);

        while(inserted == false){

            if(table[location] == null){
                table[location] = record;
                inserted = true;
                System.out.println("inserted record");
            }
            else{
                location = rehash(location, table.length);
                System.out.println("New hash location: " + location);
            }
        }

        return table;
    }

    public static int rehash(int key, int tableSize){

        int hashedKey = (key + 3) % tableSize;

        return hashedKey;
    }

    public static String retrieve(int key, String[] table){
        boolean found = false;
        String record = "";
        int location = hash(key, table.length);
        System.out.println("Searching for key " + key + " at location: " + location);

        while(!found){
            int keyFound = Integer.parseInt(table[location].substring(0, 4));

            if(key == keyFound){
                found = true;
                record = table[location];
            }
            else{
                location = rehash(location, table.length);
                System.out.println("rehashing to find key, new location: " + location);
            }
        }

        return record;
    }

}

        // for(int i = 0; i < ids.length; i++){
        //     System.out.println(ids[i]);
        // }