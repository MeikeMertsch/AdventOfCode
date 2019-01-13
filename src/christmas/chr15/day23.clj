(ns christmas.chr15.day23
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))


(defn half [status register]
	(-> (update status register #(/ % 2))
		(update :position inc)))

(defn triple [status register]
	(-> (update status register #(* % 3))
		(update :position inc)))

(defn increment [status register]
	(-> (update status register inc)
		(update :position inc)))

(defn jump [status value]
	(update status :position + (t/parse-int value)))

(defn jump-if-even [status register value]
	(->> (if (even? (status register)) (t/parse-int value) 1)
		 (update status :position +)))

(defn jump-if-one [status register value]
	(->> (if (= 1 (status register)) (t/parse-int value) 1)
		 (update status :position +)))

(def functions {"hlf" half "tpl" triple "inc" increment "jmp" jump "jie"  jump-if-even "jio" jump-if-one})

(defn parse-line [line]
	(str/split line #" |, "))

(defn parse-file [file]
	(->> (str/split-lines file)
		 (map parse-line)))

(defn tick [instructions status]
	(->> (status :position)
		 instructions
		 (#(apply (functions (first %)) (cons status (rest %))))))

(defn day23 [input]
	(->> (zipmap (range) input)
		 (#(iterate (partial tick %) {:position 0 "a" 0 "b" 0}))
		 (drop-while #(< (:position %) (count input)))
		 first
		 (#(% "b"))))

(defn day23b [input]
	(->> (zipmap (range) input)
		 (#(iterate (partial tick %) {:position 0 "a" 01"b" 0}))
		 (drop-while #(< (:position %) (count input)))
		 first
		 (#(% "b"))))
