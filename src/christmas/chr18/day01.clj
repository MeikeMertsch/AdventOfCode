(ns christmas.chr18.day01
  (:require [christmas.core :refer :all]
            [clojure.string :as str]))

(def realFile (slurp "resources/chr18/Input01real"))

(defn file_as_Ints [input]
	(->> (clojure.string/split-lines input)
		 (map (comp int bigint))))

(defn exercise01 [input]
	(apply + (file_as_Ints input)))


(def file01_as_Ints
	(->> realFile
		 (clojure.string/split-lines)
		 (map bigint)
		 (map int)))

(defn createNextObject [all_the_numbers]
	(reductions #(vector (contains? (last %) %2) (identity %2) (conj (last %) %2))
				[false [] #{}]
				all_the_numbers))

(defn exercise01b [input]
	(->> (cycle  (file_as_Ints input))
		 (reductions + 0)
		 createNextObject
	     (drop-while #(false? (first %)))
	     first
	     second))