// import java.util.Arrays;

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

  public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
    int n = nums.length;
    int[] window = new int[n];
    int sum = 0;
    for (int i = 0; i < n; i++) {
      sum += nums[i];
      if (i >= k) sum -= nums[i - k];
      if (i >= k - 1) window[i - k + 1] = sum;
    }

    int[] left = new int[n];
    int best = 0;
    for (int i = 0; i < n; i++) {
      if (window[i] > window[best]) best = i;
      left[i] = best;
    }

    int[] right = new int[n];
    best = n - k;
    for (int i = n - k; i >= 0; i--) {
      if (window[i] >= window[best]) best = i;
      right[i] = best;
    }

    int[] ans = new int[] { -1, -1, -1 };
    int maxTotal = 0;

    for (int mid = k; mid <= n - 2 * k; mid++) {
      int l = left[mid - k];
      int r = right[mid + k];

      int total = window[l] + window[mid] + window[r];

      if (ans[0] == -1 || total > maxTotal) {
        maxTotal = total;
        ans[0] = l;
        ans[1] = mid;
        ans[2] = r;
      }
    }

    return ans;
  }
}
