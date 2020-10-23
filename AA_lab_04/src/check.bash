numOfEls=300

echo "classic"
num=0
nim compile --hints:off --warnings:off classic.nim
for n in {1..10};
do
	out=`./classic $numOfEls`
	echo $out
	num=$((num+out))
done
echo "End:"
echo $((num/10))

echo "paral1"
num=0
nim compile --threads:on --hints:off --warnings:off --opt:speed -d:release parallelFir.nim
for n in {1..10};
do
	out=`./parallelFir $numOfEls`
	echo $out
	num=$((num+out))
done
echo "End:"
echo $((num/10))

echo "paral2"
num=0
nim compile --threads:on --hints:off --warnings:off --opt:speed -d:release parallelSec.nim
for n in {1..10};
do
	out=`./parallelSec $numOfEls`
	echo $out
	num=$((num+out))
done
echo "End:"
echo $((num/10))

rm classic parallelSec parallelFir
