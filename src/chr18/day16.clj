(ns christmas.day16
	(:require [clojure.string :as str]))

(defn update-fn [reg c result]
	(update-in reg [c] (constantly result)))

(defn addr [reg [a b c]]
		(update-fn reg c (+ (nth reg a)(nth reg b))))

(defn addi [reg [a b c]]
		(update-fn reg c (+ (nth reg a) b)))

(defn mulr [reg [a b c]]
		(update-fn reg c (* (nth reg a)(nth reg b))))

(defn muli [reg [a b c]]
		(update-fn reg c (* (nth reg a) b)))

(defn banr [reg [a b c]]
		(update-fn reg c (bit-and (nth reg a)(nth reg b))))

(defn bani [reg [a b c]]
		(update-fn reg c (bit-and (nth reg a) b)))

(defn borr [reg [a b c]]
		(update-fn reg c (bit-or (nth reg a)(nth reg b))))

(defn bori [reg [a b c]]
		(update-fn reg c (bit-or (nth reg a) b)))

(defn setr [reg [a _ c]]
		(update-fn reg c (nth reg a)))

(defn seti [reg [a _ c]]
		(update-fn reg c a))

(defn gtir [reg [a b c]]
		(update-fn reg c (if (< (nth reg b) a) 1 0)))

(defn gtri [reg [a b c]]
		(update-fn reg c (if (< b (nth reg a)) 1 0)))

(defn gtrr [reg [a b c]]
		(update-fn reg c (if (< (nth reg b) (nth reg a)) 1 0)))

(defn eqir [reg [a b c]]
		(update-fn reg c (if (= (nth reg b) a) 1 0)))

(defn eqri [reg [a b c]]
		(update-fn reg c (if (= b (nth reg a)) 1 0)))

(defn eqrr [reg [a b c]]
		(update-fn reg c (if (= (nth reg b) (nth reg a)) 1 0)))

(def functions [addr addi mulr muli banr bani borr bori setr seti gtir gtri gtrr eqir eqri eqrr])

(def my-func {7 mulr 3 muli 14 addi 
			6 bori 12 addr 5 borr 
			9 seti 11 eqri 4 eqir
			8 gtrr 1 eqrr 2 gtri
			13 gtir 0 setr 15 bani 10 banr})

(defn count-propers [[before instructions after]]
	(->> (map #(% before (rest instructions)) functions)
		 (filter #(= after %))
		 count))
	
(defn day16 [instruction-sets]
	(->> (map count-propers instruction-sets)
		 (filter #(<= 3 %))
		 count))

(defn mark [idx [result after func]]
	(if (= after result) 
		[idx func] 
		nil))

(defn do-propers [[before instructions after]]
;	(println before instructions after)
	(->> (map #(% before (rest instructions)) functions)
		 (map-indexed  #(mark %1 [%2 after (first instructions)]))
		 (remove nil?)))

(defn day16b [instruction-sets]
	(->> ;(remove #(contains? (into #{} (keys my-func)) (first (second %))) instruction-sets)
		 (map do-propers instruction-sets)
		 (filter #(= 1 (count %)))
		 first
		 ))


(defn apply-func [register instructions]
;	(println register instructions)
	((my-func (first instructions)) register (rest instructions)))

(defn day16c [instructions]
	(first (reduce apply-func [0 0 0 0] instructions)))
