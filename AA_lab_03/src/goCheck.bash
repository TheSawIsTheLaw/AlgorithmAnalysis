kotlinc main.kt -include-runtime -d main.jar
num=0
for n in {1..20};
do
	flex=`java -Xint -jar main.jar`
       	num=$((num+flex));
done
echo $((num/20))

