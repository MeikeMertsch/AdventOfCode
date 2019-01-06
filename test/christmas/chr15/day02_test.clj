(ns christmas.chr15.day02-test
  (:require [christmas.chr15.day02 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))


(defn parse-int [thing]
	(Integer/parseInt thing))

(defn parse-file [file]
	(->> (slurp file)
		 (str/split-lines)
		 (map #(map parse-int (re-seq #"\d+" %)))))

(def file (parse-file "resources/christmas/chr15/day02real"))

;(expect "" (parse-file file))
(expect 58 (chr/wrapper [2 3 4]))
(expect 43 (chr/wrapper [1 1 10]))

(expect 1586300 (chr/day02 file))

(expect (+ 10 24) (chr/ribbon [2 3 4]))
(expect (+ 4 10) (chr/ribbon [1 1 10]))

(expect 3737498 (chr/day02b file))
