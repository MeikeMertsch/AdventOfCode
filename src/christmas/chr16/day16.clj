(ns christmas.chr16.day16
  (:require [clojure.string :as str]))

(def value {\0 false \1 true})
(def meaning {true "1" false "0"})

(defn parse-file [file]
	   (map value file))

(defn dragon [input]
    (concat input [false] (reverse (map not input))))

(defn fill-space [length input]
    (->> (iterate dragon input)
         (drop-while #(< (count %) length))
         first
         (take length)))

(defn checksum [code]
    (->> (partition 2 code)
         (map (partial apply =))))

(defn full-checksum [code]
    (->> (iterate checksum code)
         (drop-while #(even? (count %)))
         first
         (map meaning)
         (apply str)))

(defn hide-traces [input disc-size]
    (->> (parse-file input)
         (fill-space disc-size)
         (full-checksum)))
