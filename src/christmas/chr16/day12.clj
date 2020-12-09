(ns christmas.chr16.day12
  (:require [clojure.string :as str]
  										[christmas.tools :as tools]))

(def regs {"a" 0 "b" 1 "c" 2 "d" 3})

(defn get-reg-or-nr [value registers]
    (let [value-in-reg (regs value)]
    					(if value-in-reg (get registers value-in-reg) (tools/parse-int value))))

(defn ccpy [registers x y]
				(update-in registers [(regs y)] (constantly (get-reg-or-nr x registers))))

(defn cinc [registers x]
				(update-in registers [(regs x)] inc))

(defn cdec [registers x]
				(update-in registers [(regs x)] dec))

(defn cjnz [registers x y]
    (if (not= 0 (get-reg-or-nr x registers))
        (update-in registers [4] (partial + (tools/parse-int y) -1))
        registers))

(def functions {"cpy" ccpy "jnz" cjnz "dec" cdec "inc" cinc})

(defn parse-file [file]
				(->> (str/split-lines file)
				     (map (partial re-seq #"-?\d+|cpy|jnz|inc|dec|\w"))))

(defn thing [instructions registers]
	   (-> (nth instructions (last registers))
	       (#(apply (partial (functions (first %)) registers) (rest %)))
	       (update-in [4] inc)))

(defn process [file c]
    (let [instructions (parse-file file)]
    (->> (iterate (partial thing instructions) [0 0 c 0 0])
         (drop-while #(< (last %) (count instructions)))
         first)))
