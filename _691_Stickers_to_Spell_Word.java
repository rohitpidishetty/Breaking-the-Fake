import java.util.*;

class Solution {

  private int global_level;
  private int[] ans;
  private Map<String, Integer> memo;

  private String encode(int[] arr) {
    StringBuilder sb = new StringBuilder();
    for (int x : arr) sb.append(x).append(',');
    return sb.toString();
  }

  private void solve(
    int[][] _stickers_,
    int[] _target_,
    int[] freq,
    int level
  ) {
    if (level >= this.global_level) return;

    String key = encode(_target_);
    if (memo.containsKey(key) && memo.get(key) <= level) return;
    memo.put(key, level);

    boolean done = true;
    for (int x : _target_) if (x != 0) {
      done = false;
      break;
    }
    if (done) {
      if (level < this.global_level) {
        this.ans = freq.clone();
        this.global_level = level;
      }
      return;
    }
    for (int i = 0; i < _stickers_.length; i++) {
      int _cpy_[] = _target_.clone();
      boolean useful = false;

      for (int j = 0; j < 26; j++) {
        if (_cpy_[j] > 0 && _stickers_[i][j] > 0) {
          _cpy_[j] = Math.max(0, _cpy_[j] - _stickers_[i][j]);
          useful = true;
        }
      }

      if (!useful) continue;
      freq[i]++;
      solve(_stickers_, _cpy_, freq, level + 1);
      freq[i]--;
    }
  }

  public int minStickers(String[] stickers, String target) {
    this.global_level = Integer.MAX_VALUE;
    this.memo = new HashMap<>();
    int n = stickers.length;
    this.ans = new int[n];
    int idx = 0;
    int[][] _stickers_ = new int[n][26];
    for (String sticker : stickers) {
      int[] buffer = new int[26];
      for (char ch : sticker.toCharArray()) buffer[ch - 'a']++;
      _stickers_[idx++] = buffer;
    }
    int[] _target_ = new int[26];
    for (char ch : target.toCharArray()) _target_[ch - 'a']++;
    int level;
    solve(_stickers_, _target_, new int[n], level = 0);
    int value = Arrays.stream(this.ans).sum();
    return value == 0 ? -1 : value;
  }
}

public class _691_Stickers_to_Spell_Word {

  public static void main(String[] args) {
    System.out.println(
      new Solution()
        .minStickers(new String[] { "notice", "possible" }, "basicbasic")
    );
    System.out.println(
      new Solution()
        .minStickers(new String[] { "with", "example", "science" }, "thehat")
    );
  }
}
