By Mark Levi


How to run the program:

./gradlew distTar
tar -xvzf build/distributions/comparison.tar
./comparison/bin/comparison plans.json < inputs
cat output.txt