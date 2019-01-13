(ns christmas.chr16.day02
  (:require [clojure.string :as str]))

(def direction {\U [0 -1] \L [-1 0] \R [1 0] \D [0 1]})
(def end {[0 0] 5 [-1 -1] 1 [0 -1] 2 [1 -1] 3 [-1 0] 4 [1 0] 6 [-1 1] 7 [0 1] 8 [1 1] 9})

(defn walk [pos dir]
	(->> (map + pos dir)
		 (map #(max -1 %))
		 (map #(min 1 %))))

(defn parse-line [pos line]
	(->> (map direction line)
		 (reduce walk pos)))

(defn day02 [input]
	(->> (str/split-lines input)
		 (reductions parse-line [0 0])
		 (drop 1)
		 (map end)
		 (apply str)))

(def keypad {
	1 {\D 3}
	5 {\R 6}
	9 {\L 8}
	"D" {\U "B"}

	2 {\R 3 \D 6}
	4 {\L 3 \D 8}
	"A" {\R "B" \U 6}
	"C" {\L "B" \U 8}

	3 {\U 1 \L 2 \R 4 \D 7}
	6 {\U 2 \L 5 \R 7 \D "A"}
	7 {\U 3 \L 6 \R 8 \D "B"}
	8 {\U 4 \L 7 \R 9 \D "C"}
	"B" {\U 7 \L "A" \R "C" \D "D"}})

(defn step [start dir]
	(get-in keypad [start dir] start))

(defn type [start instructions]
	(reduce step start instructions))

(defn day02b [input]
	(->> (str/split-lines input)
		 (reductions type 5)
		 rest
		 (apply str)))
		 
		;))