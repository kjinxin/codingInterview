package roblox;
/*
Given two strings s1 and s2, we will call (s1, s2) a "step" if you can form s2 by adding exactly one letter to s1 and possibly rearranging
the letters of s1.
For example:
(OF, FOR) is a step
(OF, IF) is not a step
(OF, OCT) is not a step
(ERA, EAR) is not a step
(SHE, SHEEP) is not a step
(TEE, TEST) is not a step
Given a wordlist, produce an index
w -> {w1 | (w, w1) is a step }
that associates to each word all the words in the wordlist that are a step away from it.
index = step_index(wordlist)
# Expected output (pseudocode, unordered):
NO: [ ONE, NOT, NOW ]
INTO: [ POINT ]
LEFT: []
FORM: [ FORMS ]
ONE : []
FOUR: []
FOR : [ FORM, FOUR, FROM ]
FROM: [ FORMS ]
OFF : []
FORMS: []
NOT : [ INTO ]
OF: [ FOR, OFF ]
NOW : []
POINT: []
ON: [ ONE, NOT, NOW ]
Complexity analysis variables:
n: The number of words in the word list.
k: The maximum length of any word in the word list
 */
public class WordIndexTransform {
}
