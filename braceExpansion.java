/*
Problem: https://leetcode.com/problems/brace-expansion/

Question:
You are given a string s representing a list of words. Each letter in the word has one or more options.
- If there is one option, the letter is represented as is.
- If there is more than one option, then curly braces delimit the options. For example, "{a,b,c}" represents options ["a", "b", "c"].
For example, if s = "a{b,c}", the first character is always 'a', but the second character can be 'b' or 'c'. The original list is ["ab", "ac"].

Return all words that can be formed in this manner, sorted in lexicographical order.


Example 1:

Input: s = "{a,b}c{d,e}f"
Output: ["acdf","acef","bcdf","bcef"]

Example 2:

Input: s = "abcd"
Output: ["abcd"]
 

Constraints:
- 1 <= s.length <= 50
- s consists of curly brackets '{}', commas ',', and lowercase English letters.
- s is guaranteed to be a valid input.
- There are no nested curly brackets.
- All characters inside a pair of consecutive opening and ending curly brackets are different.
*/


// TC: O(k ^ n) where k = number of blocks ({}), and n is their average length
class Solution {
    public String[] expand(String s) {
        if (s == null || s.length() == 0)
            return new String[0];
        
        List<List<Character>> chars = expandString(s);
        List<String> result = new ArrayList<>();
        backtrack(chars, 0, new StringBuilder(), result);
        
        String resultArray[] = new String[result.size()];
        for (int i = 0; i < result.size(); ++i) {
            resultArray[i] = result.get(i);
        }
        
        return resultArray;
    }
    
    private void backtrack(List<List<Character>> chars, int index, StringBuilder str, List<String> result) {
        if (index == chars.size()) {
            result.add(str.toString());
            return;
        }
        
        int length = str.length();
        List<Character> charsAtIndex = chars.get(index);
        for (int i = 0; i < charsAtIndex.size(); ++i) {
            str.append(charsAtIndex.get(i));
            backtrack(chars, index + 1, str, result);
            str.setLength(length);
        }
    }
    
    private List<List<Character>> expandString(String s) {
        List<List<Character>> chars = new ArrayList<>();
        
        int i = 0;
        while (i < s.length()) {
            List<Character> curChars = new ArrayList<>();
            char c = s.charAt(i);
            
            if (c == '{') {
                ++i;
                while (i < s.length() && s.charAt(i) != '}') {
                    if (s.charAt(i) != ',')
                        curChars.add(s.charAt(i));
                    ++i;
                }
            } else {
                curChars.add(c);
            }
            Collections.sort(curChars);
            chars.add(curChars);
            ++i;
        }
        
        return chars;
    }
}