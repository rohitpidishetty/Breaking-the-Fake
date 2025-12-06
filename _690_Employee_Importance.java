import java.util.*;

class Employee {

  public int id;
  public int importance;
  public List<Integer> subordinates;

  public Employee(int id, int imp, List<Integer> buff) {
    this.id = id;
    this.importance = imp;
    this.subordinates = buff;
  }
}

class Solution {

  private Map<Integer, Buffer> map;

  private class Buffer {

    public int importance;
    public List<Integer> subordinates;

    public Buffer(int imp, List<Integer> buff) {
      this.importance = imp;
      this.subordinates = buff;
    }
  }

  public int getImportance(List<Employee> employees, int id) {
    this.map = new HashMap<>();
    for (Employee emp : employees) {
      map.put(emp.id, new Buffer(emp.importance, emp.subordinates));
    }

    return dfs(id);
  }

  private int dfs(int id) {
    if (!map.containsKey(id)) return 0;
    Buffer o = map.get(id);
    int imp = o.importance;
    List<Integer> buffer = o.subordinates;
    if (buffer == null) return imp;
    int ans = imp;
    for (Integer n : buffer) ans += dfs(n);
    return ans;
  }
}

public class _690_Employee_Importance {

  public static void main(String[] args) {
    System.out.println(
      new Solution()
        .getImportance(
          Arrays.asList(
            new Employee(1, 5, Arrays.asList(2, 3)),
            new Employee(2, 3, null),
            new Employee(3, 3, null)
          ),
          1
        )
    );
  }
}
