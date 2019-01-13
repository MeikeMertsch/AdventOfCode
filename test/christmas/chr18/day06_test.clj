(ns christmas.chr18.day06-test
  (:require [christmas.chr18.day06 :as chr]
  		  	[expectations :refer :all]
            [christmas.core :refer :all]
            [clojure.string :as str]))
(comment
(defn parse [stri]
	(int (bigint stri)))

(defn getFile [fileName]
	(->> (slurp fileName)
		 (clojure.string/split-lines)
		 (map #(str/split % #","))
		 (map #(map (comp parse str/trim) %))))

(def realFile (getFile "resources/chr18/Input06real"))
(def file (getFile "resources/chr18/Input06"))


(expect 2 (chr/cab [1 1] [2 2]))
(expect 2 (chr/cab [2 2] [1 1]))

(expect [[1 1][8 9]] (chr/board file))
(expect 17 (chr/exercise06 file))


(expect #{[3 4] [5 5]} (into #{}(chr/whoIsInMiddle file)))

(expect 17 (chr/countcoordsonboard [5 5](chr/allnearest (chr/allboardcoords file) file)))
(expect 9 (chr/countcoordsonboard [3 4](chr/allnearest (chr/allboardcoords file) file)))

(expect 17 (chr/countPerCoords (chr/whoIsInMiddle file) (chr/allnearest (chr/allboardcoords file) file)))

;_______________________________________

(expect 16 (chr/exercise06b file 32))
;(expect-focused 16 (chr/exercise06b realFile 10000)) ; 61s
(expect 30 (chr/calcalldistances [4 3] file))
)