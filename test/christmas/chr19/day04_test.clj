(ns christmas.chr17.day04-test
 (:require [christmas.chr17.day04 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))
(comment
(defn get-file [file-name]
	(->> (slurp file-name)
		 str/split-lines
		 (map #(str/split % #" "))))

(def file (get-file "resources/chr17/day04real"))

(expect 119 (chr/day04 file))
)