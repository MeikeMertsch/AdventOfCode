(ns christmas.chr20.day14
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))


(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       
       ))

(defn bump [masks number]
  (reduce (fn [num [pos act]] (if (= \0 act) (bit-clear num pos) (bit-set num pos))) number masks)
  )

(defn update-num [mapping instruction]
  (->> (re-seq #"\d+" instruction)
       (map t/parse-int)
       (#(update mapping :mems (fn [mems] (assoc mems (first %) (bump (:mask mapping)(last %))))))
       )
  )

(defn tick [mapping instruction]
  (->> (if (str/starts-with? instruction "mask")
       (assoc mapping :mask (->> (re-find #"[X|0|1]+" instruction) (map vector (range 35 -1 -1)) (remove #(= \X (last %)))))
         (update-num mapping instruction)
      ; (update mapping :mems  (->> (#(assoc % (first %) (last %))) (fn [something] something))));(#(assoc (:mems mapping) (first %) (new-num (:mask mapping) (last num)))))))
       )))

(defn day14 [input]
  (->> (parsefile input)
       (reduce tick {:mems {}})
       (:mems)
       (map last)
       (apply +)
       ))



