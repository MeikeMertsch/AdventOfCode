(ns christmas.day14
	(:require [clojure.string :as str]))

(defn parse-int [string]
	(Integer/parseInt (str string)))

(defn nth- [string pos]
	(parse-int (nth string pos)))

(defn clean-pos [string pos]
	(let [length (count string)]
		(if (<= length pos) (- pos length) pos)))

(defn tick [string pos1 pos2]
	(let [elf1 (nth- string pos1)
		  elf2 (nth- string pos2)]
	(->> (+ elf1 elf2)
		 (str string)
		 (#(vector %
		 		   (clean-pos % (+ 1 pos1 elf1))
		 		   (clean-pos % (+ 1 pos2 elf2)))))))

(defn day14 [input]
	(->> (reduce (fn [[string pos1 pos2] _] (tick string pos1 pos2)) ["3710" 0 1] (range 2 (+ 10 input)))
		 first
		 (drop input)
		 (take 10)
		 (apply str)))


(defn day14_a [input]
	(->> (reduce (fn [[string pos1 pos2] _] (tick string pos1 pos2)) ["3710" 0 1] (range 2 (+ 10 input)))
		 first
		 (drop input)
		 (take 10)
		 (apply str)))

;---------------------

(defn add [recipes x]
	(if (< 9 x) 
		(conj recipes 1 (- x 10))
		(conj recipes x)))

(defn tick-2 [recipes pos1 pos2]
	(let [elf1 (nth recipes pos1)
		  elf2 (nth recipes pos2)]
	(->> (+ elf1 elf2)
		 (add recipes)
		 (#(vector %
		 		   (clean-pos % (+ 1 pos1 elf1))
		 		   (clean-pos % (+ 1 pos2 elf2)))))))

(defn day14-part1 [input]
	(->> (reduce (fn [[recipes pos1 pos2] _] (tick-2 recipes pos1 pos2)) [[3 7 1 0] 0 1] (range 2 (+ 10 input)))
		 first
		 (drop input)
		 (take 10)))

(defn last-index [input recipes]
	(let [length (count input)
		  length-r (count recipes)
		  diff (- length-r length)]
		(cond 
			(<= diff 0) nil
			(= input (subvec recipes diff)) diff
			(= input (take length (subvec recipes (dec diff)))) (dec diff)
		)))


(defn day14b-part2 [input]
	(->> (reductions (fn [[recipes pos1 pos2] _] (tick-2 recipes pos1 pos2)) [[3 7 1 0] 0 1] (range))
		 (map first)
		 (map #(last-index input %))
		 (drop-while nil?)
		 first
;		 (take 10)
		 ))




