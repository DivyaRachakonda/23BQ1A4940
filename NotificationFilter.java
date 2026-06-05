import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Notification implements Comparable<Notification> {
    String id;
    String type; // Placement, Result, Event
    String message;
    LocalDateTime timestamp;

    public Notification(String id, String type, String message, String timestampStr) {
        this.id = id;
        this.type = type;
        this.message = message;
        // Parser for format: "2026-04-22 17:51:30"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.parse(timestampStr, formatter);
    }

    // Get numerical priority weight based on rules: Placement > Result > Event
    public int getWeight() {
        switch (this.type) {
            case "Placement": return 3;
            case "Result": return 2;
            case "Event": return 1;
            default: return 0;
        }
    }

    @Override
    public int compareTo(Notification other) {
        // First compare weight
        if (this.getWeight() != other.getWeight()) {
            return Integer.compare(this.getWeight(), other.getWeight());
        }
        // If weights match, compare timestamps (newer is higher priority)
        return this.timestamp.compareTo(other.timestamp);
    }
}

public class NotificationFilter {
    public static void main(String[] args) {
        // Simulate notifications arriving from the API
        List<Notification> incomingNotifications = new ArrayList<>();
        incomingNotifications.add(new Notification("1", "Result", "mid-sem", "2026-04-22 17:50:54"));
        incomingNotifications.add(new Notification("2", "Placement", "CSX Corporation hiring", "2026-04-22 17:51:18"));
        incomingNotifications.add(new Notification("3", "Event", "farewell", "2026-04-22 17:51:06"));
        incomingNotifications.add(new Notification("4", "Result", "final-sem", "2026-04-22 17:51:30"));

        // Use a Min-Heap (PriorityQueue) limited to size 10 to maintain top elements efficiently
        PriorityQueue<Notification> topNotifications = new PriorityQueue<>(10);

        for (Notification n : incomingNotifications) {
            if (topNotifications.size() < 10) {
                topNotifications.add(n);
            } else if (n.compareTo(topNotifications.peek()) > 0) {
                topNotifications.poll(); // Remove least important from our top 10 list
                topNotifications.add(n); // Add the new higher priority one
            }
        }

        // Output results
        System.out.println("--- Top Priority Notifications ---");
        while (!topNotifications.isEmpty()) {
            Notification current = topNotifications.poll();
            System.out.println("[" + current.type + "] - " + current.message + " (" + current.timestamp + ")");
        }
    }
}