(ns christmas.day19
	(:require [clojure.string :as str]))

(defn parse-int [thing]
	(Integer/parseInt thing))

(defn update-fn [reg c result]
	(update-in reg [c] (constantly result)))

(defn addr [reg [a b c]]
		(update-fn reg c (+ (nth reg a)(nth reg b))))

(defn addi [reg [a b c]]
		(update-fn reg c (+ (nth reg a) b)))

(defn mulr [reg [a b c]]
		(update-fn reg c (*' (nth reg a)(nth reg b))))

(defn muli [reg [a b c]]
		(update-fn reg c (*' (nth reg a) b)))

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

(def funcs {"addr" addr "addi" addi "mulr" mulr "muli" muli "banr" banr "bani" bani "borr" borr "bori" bori "setr" setr "seti" seti "gtir" gtir "gtri" gtri "gtrr" gtrr "eqir" eqir "eqri" eqri "eqrr" eqrr})

(defn handle-row [instructions]
	(vector (first instructions)
			(map parse-int (rest instructions))))

(defn exec [reg instruction]
;(println reg instruction)
	((funcs (first instruction)) reg (last instruction)))

(defn next-step [register instructions ip-reg]
;(println register ip-reg)
;(println (nth instructions (inc (nth register ip-reg))))
(time
	(->> (nth instructions (nth register ip-reg))
		 (exec register)
		 (#(update-in % [ip-reg] inc)))))

(defn day19 [ip-reg instructions]
	(->> (iterate #(next-step % instructions ip-reg) [0 0 0 0 0 0])
		 ;(drop 150)
		 (drop-while #(< (nth % ip-reg) (dec (count instructions))))
		 first
		 ))

	
	




















