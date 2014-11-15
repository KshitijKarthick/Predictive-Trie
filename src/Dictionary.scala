import scala.io.Source
import scala.collection.mutable.Stack
import java.io.FileNotFoundException
import java.lang.NumberFormatException
class Node{
	var rank=0;
	var terminal=0;
	var link=scala.collection.mutable.Map[Char,Node]()
}
class Trie{
	val root=new Node()
	var flag=false
	def this(args: Array[String]){
		this()
		for(filename <- args){
			try{
				for(line <- Source.fromFile(filename).getLines()){
					for(word <- line.stripLineEnd.split(' '))
						this.addWord(word)
				}
				println("\nFile "+filename+" has Loaded Successfully in the Dictionary")
				flag=true
			}
			catch{
				case ex: FileNotFoundException =>{
					println("File "+filename+" is Missing")
				}
			}
		}
		if(flag==false){
			println("Program Will Exit Now!!!")
			System.exit(-1)
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
	def prediction(word: String,num :Int):Seq[String]={
		var list=Stack[String]()
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
			//println(word)
			traversal(temp,list,word)
		}
		def traversal(temp: Node,list: Stack[String],word: String):Unit={
			if(noOfWords>0){
				for(ch <- sort(temp)){
					//println(word+":"+key)
					if(temp.terminal==1){
						//println("test:"+ch+":"+word+":"+temp.rank)
						list.push(word)
						//println(word)
						noOfWords-=1
					}
					traversal(temp.link(ch),list,word+ch)	
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
		return list.reverse.seq.distinct
	}
}
object Dictionary{
	def main(args:Array[String]):Unit={
		val t1=new Trie(args)
		var noOfWords=10
		println("\nEnter Number of Predictions Required:")
		try{
			noOfWords=(Console.readLine()).toInt
		}
		catch{
			case ex: NumberFormatException => {
            println("Invalid Input, Default value is Considered")
         }
		}
		println("\nEnter a Word to be Searched from the Dictionary")
		var x=Console.readLine()
		while(x.toLowerCase!="end" && x!="")
		{
			if(t1.searchWord(x))
				println("Search Successful : "+x+" is Found")
			else
				println("Search Unsuccessful :"+x+" is not Found")
			println("Predictions :")
			(t1.prediction(x,noOfWords)).foreach(x => if(x!=null)println(x))
			println("\nEnter a Word to be Searched from the Dictionary")
			x=Console.readLine()
		}
	}
}