(ns christmas.chr18.day20
	(:require [clojure.string :as str]))

(def pattern #"\([SWEN\|]*\)")

(defn longest [replacement]	
	(let [cand (map #(re-find #"[SWEN]+" %) (str/split replacement #"\|"))
		  text (if (some nil? cand) "" (first (sort-by count > cand)))]
;(println replacement text)
		text))

(defn replace-inner [text]
	(->> (re-find pattern text)
		 (#(str/replace-first text pattern (longest %)))))

(defn day20 [text]
	(->> (iterate replace-inner text)
		 (drop-while #(str/includes? % "("))
		 first
		 count
		 (- 2)
		 (* -1)
		 ))