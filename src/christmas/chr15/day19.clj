(ns christmas.chr15.day19
  (:require [clojure.string :as str]))

(defn parse-instructions [file]
	(->> (str/split-lines file)
		 (map #(str/split % #" => "))))

(defn look-for-index [start element string]
	(str/index-of string element (inc start)))

(defn changed-molecule [instruction string position]
	(str (subs string 0 position) 
		 (second instruction) 
		 (subs string (+ position (count (first instruction))))))

(defn change-molecule [instruction string]
	(let [molecule (first instruction)]
	(->> (iterate #(look-for-index % molecule string) 0)
		 (#(if (str/starts-with? string molecule) % (drop 1 %))) 
		 (take-while some?)
		 (map (partial changed-molecule instruction string)))))

(defn day19 [instructions string]
	(->> (parse-instructions instructions)
		 (mapcat #(change-molecule % string))
		 distinct
		 count))

(defn tick [rev-instructions string]
	(->> (mapcat #(change-molecule % string) rev-instructions)
		 first))

(defn day19b [instructions string]
	(->> (parse-instructions instructions)
		 (map reverse)
		 (#(iterate (partial tick %) string))
		 (take-while (partial not= "e"))
		 count))