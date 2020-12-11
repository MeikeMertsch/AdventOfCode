(ns christmas.chr20.day10
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(def magic {1 1 2 1 3 2 4 4 5 7 6 13}) ;(each number being the sum of the previous three and then replacing 0 with 1. Mapped to position

(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (map t/parse-int)
       (sort)))

(defn day10 [input]
  (->> (parsefile input)
       (concat [0])
       (partition 2 1)
       (map #(- (last %) (first %)))
       (group-by identity)
       (#(* (count (% 1)) (inc (count (% 3)))))))

(defn seqence [lists element]
  (if (= element (inc (t/llast lists)))
    (update-in lists [(dec (count lists))] #(conj % element))
    (conj lists [element])))

(defn day10b [input]
  (->> (parsefile input)
       (reduce seqence [[0]])
       (map count)
       (map magic)
       (apply *)))
