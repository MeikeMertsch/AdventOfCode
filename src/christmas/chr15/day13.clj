(ns christmas.chr15.day13
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(defn all-guests [input]
	(->> (str/split-lines input)
		 (map #(re-find #"\w+" %))
		 distinct))

(defn get-number [text]
	(let [number (t/parse-int (re-find #"\d+" text))]
		(if (re-find #"lose" text)
			(- number)
			number)))

(defn parse-line [line]
	(->> (str/split line #" would | happiness units by sitting next to |\.")
		 (#(vector (vector (first %) (last %))
		 		   (get-number (second %))))))

(defn parse-file [file]
	(->> (str/split-lines file)
		 (map parse-line)
		 (apply concat)
		 (apply hash-map)))

(defn new-combo [all-destinations destinations]
	(for [x all-destinations
		  y destinations
		  :let [result (conj y x)]
		  :when (apply distinct? result)]
		result))

(defn combos [destinations]
	(->> (iterate (partial new-combo destinations) (map vector destinations))
		 (take (count destinations))
		 last
		 (map #(conj % (first %)))))

(defn distance [distances route]
	(->> (partition 2 1 route)
		 (#(concat % (map reverse %)))
		 (map #(distances % 0))
		 (apply +)))

(defn day13 [input]
	(->> (all-guests input)
		 combos
		 (map (partial distance (parse-file input)))
		 (apply max)))

(defn day13b [input]
	(->> (cons "me" (all-guests input))
		 combos
		 (map (partial distance (parse-file input)))
		 (apply max)))

