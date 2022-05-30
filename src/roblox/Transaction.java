package roblox;
/*
技术电面说的一个简单的key-value store，但要实现transaction，类似于你可以put(a, 1)，然后get (a)直接返回1，你也可以call一个function begin() ，
自己要实现这个function，然后宣布进入transaction，然后put(a,1)，这个时候如果马上get(a)，你还是要返回1，但put完之后，你可以call rollback()，
这个function也要自己实现，rollback() 之后你再call get(a)，就返回null，你也可以 call commit(）,那么这个结果就storage就持久化了。
follow up是如何实现多transaction，transaction可以嵌套，就是在一个transaction下面可以再开一个新的子transaction，返回一个transaction id，
最后call commit rollback带‍‌这个id，会一次性commit和rollback所有的这个transaction和他的子transaction.
然后又问了我现在市面上的db的支持transaction的类型有哪些，把最低等的read commited到最复杂的linearliazation都说了一遍）。
整个engineer和manager都很丧，气压很低，感觉也不太好，但是manager似乎很牛，以前是另外一个大公司某个org 的CTO。
 */
public class Transaction {
}
