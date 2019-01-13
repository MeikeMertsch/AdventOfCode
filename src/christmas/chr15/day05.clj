(ns christmas.chr15.day05
  (:require [clojure.string :as str]))

(defn has-three-vowels? [string]
	(->> (filter #{\a \e \i \o \u} string)
		 count
		 (< 2)))

(defn has-duplicates? [string]
	(< (count (dedupe string)) (count string)))

(defn not-has-forbidden-combs? [string]
	(not-any? (partial str/includes? string) ["ab" "cd" "pq" "xy"]))

(defn nice? [string]
	(->> [has-three-vowels? has-duplicates? not-has-forbidden-combs?]
		 (map #(% string))
		 (every? true?)))
	
(defn day05 [input]
	(count (filter nice? input)))

(defn pattern [pair]
	(->> (apply str pair)
		 (#(str % "|" (str/reverse %)))
		 re-pattern))

(defn check-replace [string [pair _]]
	(let [regex (pattern pair)]
		(->> (str/replace-first string regex "")
			 (re-find regex)
			 some?)))

(defn contains-dupe? [string]
	(-> (re-pattern "([a-z]{2}).*\\1")
		(re-find string)
		some?))

(defn contains-repeater? [string]
	(->> (partition 3 1 string)
		 (some (fn [[x _ y]] (= x y)))))

(defn nice-2? [string]
	(->> [contains-repeater? contains-dupe?]
		 (map #(% string))
		 (every? true?)))

(defn day05b [input]
	(count (filter nice-2? input)))

