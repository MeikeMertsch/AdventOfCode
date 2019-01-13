(ns christmas.chr18.day16-test
 (:require [christmas.chr18.day16 :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))

(comment
(defn parse-int [thing]
	(Integer/parseInt thing))

(defn handle-row [my-string]
	(-> (re-seq #"\d+" my-string)
		(#(mapv parse-int %))))

(defn per-triple [triple]
	(->> (take 3 triple)
		 (mapv handle-row)))

(defn get-file [file-name]
	(->> (slurp file-name)
		 str/split-lines
		 (partition-all 4)
		 (map per-triple)))

(defn get-fileb [file-name]
	(->> (slurp file-name)
		 str/split-lines
		 (map handle-row)))

(def file (get-file "resources/chr18/Input16a"))
(def file-b (get-fileb "resources/chr18/Input16b"))

(expect [4 4 2 1] (chr/addr [4 2 2 1] [2 2 1]))
(expect 3 (parse-int "3"))

(expect 3 (chr/count-propers [[3, 2, 1, 1] [9 2 1 2] [3, 2, 2, 1]]))

(expect 1 (chr/day16 [[[3, 2, 1, 1] [9 2 1 2] [3, 2, 2, 1]]]))
(expect 509 (chr/day16 file))

(expect 496 (chr/day16c file-b))

;(expect "" (chr/day16b file))
)