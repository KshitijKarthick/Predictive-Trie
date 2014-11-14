import scala.io.Source
import scala.collection.mutable.Stack
import scala.collection.mutable.Set
class Node{
	var rank=0;
	var terminal=0;
	var link=scala.collection.mutable.Map[Char,Node]()
}
class Trie{
	val root=new Node()
	def this(args: Array[String]){
		this()
		for(filename <- args){
			for(line <- Source.fromFile(filename).getLines()){
				for(word <- line.stripLineEnd.split(' '))
					this.addWord(word)
			}
			println("\nFile "+filename+" has Loaded Successfully in the Dictionary")
		}
	}
	def addWord(word: String):Unit={
		var temp=root
		for(ch <- word){
			if(!temp.link.contains(ch))
			temp.link+=(ch->new Node())
			temp.rank+=1
			temp=temp.link(ch)
		}
		temp.terminal=1
	}
	def searchWord(word: String):Boolean={
		var temp=root
		for(ch <- word){
			if(!temp.link.contains(ch))
			return false
			temp=temp.link(ch)
		}
		if(temp.terminal==0)
		return false
		return true
	}
	def prediction(word: String,num :Int):Set[String]={
		var list=Set(word)
		var noOfWords=num
		predictWord(word)
		def predictWord(word: String):Unit={
			var temp=root
			for(ch <- word){
				if(temp.link.contains(ch) && ch!=',' && ch!='.' && ch!=' ' && ch!='?' && ch!='!' && ch!=';')
					temp=temp.link(ch)
				else
					return
			}
			traversal(temp,list,word)
		}
		def traversal(temp: Node,list: Set[String],word: String):Unit={
			if(noOfWords>0){
				for(x <- sort(temp)){
					val key=x
					//println(word+":"+key)
					if(temp.terminal==1){
						list+=word
						//println(word)
						noOfWords-=1
					}
					traversal(temp.link(key),list,word+key)	
				}
			}
		}
		def sort(temp: Node):Stack[Char]={
			var data=Stack[Char]()
			temp.link.toList sortBy ( _._1 ) foreach {
    			case (key, value) =>
    				//println(key+":"+value)
        			data.push(key)
			}
			data=data.reverse
			return data
		}
		return list
	}
}
object Dictionary{
	def main(args:Array[String]){
		val t1=new Trie(args)
		println("\nEnter Number of Predictions Required:")
		var noOfWords=(Console.readLine()).toInt
		println("\nEnter a Word to be Searched from the Dictionary")
		var x=Console.readLine()
		while(x.toLowerCase!="end" && x!="")
		{
			if(t1.searchWord(x))
				println("Search Successful : "+x+" is Found")
			else
				println("Search Unsuccessful :"+x+" is not Found")
			(t1.prediction(x,noOfWords)).foreach(x => if(x!=null)println(x))
			println("\nEnter a Word to be Searched from the Dictionary")
			x=Console.readLine()
		}
	}
}