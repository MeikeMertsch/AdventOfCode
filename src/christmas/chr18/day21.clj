(ns christmas.chr18.day21
	(:require [clojure.string :as str]))

(defn parse-int [thing]
	(Integer/parseInt thing))

(defn update-fn [reg c result]
	(update-in reg [c] (constantly result)))

(defn addr [reg [a b c]]
		(update-fn reg c (+ (nth reg a)(nth reg b))))

(defn addi [reg [a b c]]
		(update-fn reg c (+ (nth reg a) b)))

(defn muli [reg [a b c]]
		(update-fn reg c (*' (nth reg a) b)))

(defn bani [reg [a b c]]
		(update-fn reg c (bit-and (nth reg a) b)))

(defn bori [reg [a b c]]
		(update-fn reg c (bit-or (nth reg a) b)))

(defn setr [reg [a _ c]]
		(update-fn reg c (nth reg a)))

(defn seti [reg [a _ c]]
		(update-fn reg c a))

(defn gtir [reg [a b c]]
	(let [drops (< (nth reg b) a)]
	;(if drops (spit "resources/day21-25/Output21b" (str (apply str (interleave (map (partial nth reg) [4]) (repeat " "))) "\n") :append true))
	(if drops (spit "resources/day21-25/Output21b" (str (nth reg 4) "\n") :append true))
		(update-fn reg c (if drops 1 0))))

(defn gtrr [reg [a b c]]
		(update-fn reg c (if (< (nth reg b) (nth reg a)) 1 0)))

(defn eqri [reg [a b c]]
		(update-fn reg c (if (= b (nth reg a)) 1 0)))

(defn extra [reg [a b c]]
		(update-fn reg c (quot (nth reg a) b)))
;extra 5 256 5


(defn eqrr [reg [a b c]]
;	(println "XXXX" reg "XXXX")
		(update-fn reg c (if (= (nth reg b) (nth reg a)) 1 0)))

(def funcs {"addr" addr "addi" addi "muli" muli "bani" bani "bori" bori "setr" setr "seti" seti "gtrr" gtrr "gtir" gtir "eqri" eqri "eqrr" eqrr "extra" extra})

(defn handle-row [instructions]
	(vector (first instructions)
			(map parse-int (rest instructions))))

(defn exec [reg instruction]
	((funcs (first instruction)) reg (last instruction)))

(defn next-step [register instructions ip-reg]
	(->> (nth instructions (nth register ip-reg))
		 (exec register)
		 (#(update-in % [ip-reg] inc))))

(defn day21 [ip-reg instructions]
	(->> (iterate #(next-step % instructions ip-reg) [0 0 0 0 0 0])
		 (drop-while #(< (nth % ip-reg) (dec (count instructions))))
		 first
		 (#(nth % 4))
		 ))


(defn day21b [ip-reg instructions]
	(->> (iterate #(next-step % instructions ip-reg) [0 0 0 0 0 0])
		 (remove #(< (nth % ip-reg) (dec (count instructions))))
		 (map #(nth % 4))
		 (reductions #(conj %1 %2) [])
		(drop 1)
		 (take-while #(apply distinct? %))
		 last	
		 last))	 























