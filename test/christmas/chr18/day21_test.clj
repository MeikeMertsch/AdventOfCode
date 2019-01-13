(ns christmas.chr18.day21-test
 (:require [christmas.chr18.day21 :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))

(defn get-file [file]
	(->> (slurp file)
		 str/split-lines
		 (map #(str/split % #" "))
		 (map chr/handle-row)))

(def file (get-file "resources/chr18/Input21real"))
(def faster-file (get-file "resources/chr18/Input21changed"))
(def input 2)

;(expect 12213578 (chr/day21 input file))
;(expect-focused 5310683 (chr/day21b input faster-file))


