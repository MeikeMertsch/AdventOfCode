(ns christmas.chr16.day15
    (:require [christmas.tools :as tools])
    (:require [clojure.string :as str]))


(defn parse-file [file]
	   (->> (str/split-lines file)
	        (map (partial re-seq #"\d+"))
	        (map (partial map tools/parse-int))))

(defn cyc[[number slots _ start-pos]]
    (->> (range slots)
         cycle
         (drop start-pos)
         (drop number)))

(defn get-time [file]
    (->> (parse-file file)
    					(map cyc)
    					(apply map +)
    					(take-while pos?)
    					count))

(defn get-time2 [file]
    (->> (parse-file file)
         (cons [7 11 0 0])
    					(map cyc)
    					(apply map +)
    					(take-while pos?)
    					count))
