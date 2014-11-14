# Predictive Trie - Proof of Concept :
## Program Features :
   * The Predictive Algorithm predicts words based on specific user vocabulary and sets ranks for each of the words.
   * Rank Based Predictive Algorithm which keeps evolving based on the Usage.
   * Accuracy of the Algorithm improves based on the usage.

## Program Details :
   * Program is written in Scala.
   * Employs trie's to store and search for the word.
   * Creates a trie consisting of a Map data structure in each Node.
   * The Map data structure adds the character only if created,space is not preallocated for all characters.
   * Selects User Defined predictions from all the words with the highest Rank.
   * Rank of a word increases based on the usage of the word.
   * Searching for a word will require n iterations [ n <= wordlength ].
   * Dictionary.txt stores all the words used.

## Program Execution :
```
  # Windows and Posix OS Compliant.
  > scalac Dictionary.scala
  > scala Dictionary Dictionary.txt
```

## Internal Implementation :
    * Trie => ``` Creates a trie composed of all Nodes. ```
    * Node
        * Map => ``` Key -> Character, Value -> Node. ```
        * Rank => ``` Stores Rank for Current Character. ```
        * Terminal => ``` Indicates End of Word. ```

## To Do :
  * Imporovement in Rank Storage.
  * GUI Interface.
