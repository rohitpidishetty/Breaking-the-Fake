import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// class Solution {

//   private int max = Integer.MIN_VALUE;
//   private int[] res;

//   private void solve(
//     int idx,
//     int[] nums,
//     int k,
//     int count,
//     int[] buffer,
//     int[] indices
//   ) {
//     if (count == 3) {
//       int n = Arrays.stream(buffer.clone()).sum();
//       if (n > max) {
//         this.res = indices.clone();
//         max = n;
//       }
//       return;
//     }

//     if (idx + k > nums.length) return;

//     int sum = 0;
//     for (int i = idx; i < idx + k; i++) sum += nums[i];
//     buffer[count] = sum;
//     indices[count] = idx;

//     solve(idx + k, nums, k, count + 1, buffer.clone(), indices);
//     solve(idx + 1, nums, k, count, buffer.clone(), indices);
//   }

//   public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
//     int count = 0;
//     this.res = new int[3];
//     int[] buffer = new int[3];
//     int[] indices = new int[3];
//     solve(0, nums, k, count, buffer, indices);

//     return this.res;
//   }
// }

// public class _689_Maximum_Sum_of_3_Non_Overlapping_Subarrays {

//   public static void main(String[] args) {
//     System.out.println(
//       Arrays.toString(
//         new Solution()
//           .maxSumOfThreeSubarrays(new int[] { 1, 2, 1, 2, 6, 7, 5, 1 }, 2)
//       )
//     );
//   }
// }
class Solution {

  private Map<String, Integer> memo;
  private int[] buff;

  private void solve(
    int index,
    ArrayList<Integer> ans,
    int[] nums,
    int k,
    int count,
    ArrayList<Integer> prefix
  ) {
    if (count == 0) {
      this.buff = ans.stream().mapToInt(Integer::intValue).toArray();
      return;
    }
    if (index >= prefix.size()) return;

    int consider_i =
      prefix.get(index) + presolve(index + k, ans, nums, k, count - 1, prefix);
    int dont_consider_i = presolve(index + 1, ans, nums, k, count, prefix);
    if (consider_i >= dont_consider_i) {
      ans.add(index);
      solve(index + k, ans, nums, k, count - 1, prefix);
      ans.remove(ans.size() - 1);
    } else solve(index + 1, ans, nums, k, count, prefix);
  }

  private int presolve(
    int index,
    ArrayList<Integer> ans,
    int[] nums,
    int k,
    int count,
    ArrayList<Integer> prefix
  ) {
    if (count == 0) return 0;
    if (index >= prefix.size()) return Integer.MIN_VALUE;

    String key = index + ":" + count;
    if (this.memo.containsKey(key)) return this.memo.get(key);

    int consider_i =
      prefix.get(index) + presolve(index + k, ans, nums, k, count - 1, prefix);
    int dont_consider_i = presolve(index + 1, ans, nums, k, count, prefix);
    int n = Math.max(consider_i, dont_consider_i);
    this.memo.put(key, n);
    return n;
  }

  public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
    this.memo = new HashMap<>();
    ArrayList<Integer> ans = new ArrayList<>();
    int count = 3;
    ArrayList<Integer> prefix = new ArrayList<>();
    int i = 0, j = 0;
    int sum = 0;
    for (j = 0; j < nums.length; j++) {
      sum += nums[j];
      if (j - i + 1 == k) {
        prefix.add(sum);
        sum -= nums[i];
        i++;
      }
    }

    solve(0, ans, nums, k, count, prefix);

    return this.buff;
  }
}

public class _689_Maximum_Sum_of_3_Non_Overlapping_Subarrays {

  public static void main(String[] args) {
    System.out.println(
      Arrays.toString(
        new Solution()
          .maxSumOfThreeSubarrays(new int[] { 1, 2, 1, 2, 6, 7, 5, 1 }, 2)
      )
    );
  }
}
