(ns christmas.day02
  (:require [christmas.core :refer :all]
            [clojure.string :as str]))


(defn partitionByIdentity [the_string]
	(->> (sort the_string)
		 (partition-by identity)))

(defn containsTwo [my_set]
    (some #(= (count %) 2) my_set))

(defn containsThree [my_set]
    (some #(= (count %) 3) my_set))

(defn containsBigger [my_set]
	(some #(< 3 (count %)) my_set))

(defn countTwos [my_set]
	(println my_set)
	(->> (filter #(= (count %) 2) my_set)
         count
		))

(defn countOfAllTwo [source]
	(->> (map partitionByIdentity source)
		 (filter #(containsTwo %))
		 count
		 ))

(defn countOfAllThree [source]
	(->> (map partitionByIdentity source)
		 (filter #(containsThree %))
		 count
		 ))

(defn exercise02 [source]
	(* (countOfAllTwo source) (countOfAllThree source)))

(defn returnIDBox [my_set]
	(->> (map partitionByIdentity my_set)
		 (filter #(= 4 (countTwos %)))))

(defn crossbreed [my_entry my_set]
	(map #(map str my_entry %) my_set))

(defn exercise02b [source]
	(->> (mapcat #(crossbreed % source) source)
		(map (fn [n] (filter #(= (first %) (last %)) n)))
		(filter #(= (count %) (dec (count (first source)))))
		first
		(map first)
		(apply str)
))