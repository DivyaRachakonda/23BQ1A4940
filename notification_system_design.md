
# Campus Notification System - Stage 1 System Design

## 1. Core Architecture & Requirements
This system acts as a high-throughput priority buffer for campus updates, managing incoming notifications and displaying the top 10 most critical alerts based on specific structural rules.

### Priority Weighting Logic
Incoming notifications are parsed and categorized into three distinct operational tiers:
* **Placement Notices (Weight 3):** Critical career updates requiring immediate attention.
* **Result Declarations (Weight 2):** Academic updates with high user engagement.
* **Event Alerts (Weight 1):** Standard campus activities and informational updates.

### Tie-Breaking Metric
When two notifications share an identical priority category weight, the system applies a chronological recency filter. The application evaluates the granular ISO 8601 timestamp string (`Timestamp`), shifting the most recently published notification to a higher priority position.

---

## 2. Data Structure Selection & Efficiency

### Selected Structure: Min-Heap (`PriorityQueue`)
To maintain the "Top 10" entries dynamically with optimal performance, a **Min-Heap** bounded to a maximum capacity of $K = 10$ is implemented in the core algorithm (`NotificationFilter.java`).

### Algorithmic Complexity Evaluation
Instead of collecting all system elements in a primitive array and sorting them entirely—which demands a heavy operational cost of $O(N \log N)$—the bounded Min-Heap processes elements sequentially:

* **Time Complexity:** For an unpredictable data stream of $N$ notifications, inserting elements into a maximum heap of size 10 takes **$O(N \log 10)$**, which scales linearly as **$O(N)$**. 
* **Space Complexity:** The operational memory footprint remains completely constant at **$O(1)$** because the heap never retains more than 10 records at any given instant, protecting system resources from memory overflow.

---

## 3. Data Flow Validation
1.  **Authentication:** The backend interface (`Auth.java`) communicates with the centralized identity gateway via HTTP POST to secure a functional JWT Bearer token.
2.  **Ingestion & Filtering:** The system fetches live data collections, stream-filters the payloads based on category weights, and executes a sliding retention window via the Min-Heap structure.
3.  **Frontend Render:** The client interface layer (`dashboard.html`) mirrors this processing hierarchy asynchronously via custom JavaScript handlers, dynamically creating a responsive layout with clean status blocks and prioritized lists.
