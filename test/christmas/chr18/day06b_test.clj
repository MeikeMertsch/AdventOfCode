(ns christmas.chr18.day06b-test
  (:require [christmas.chr18.day06b :as chr]
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
(def file (getFile "resource/chr18/Input06"))


(expect '((1 1) (1 6) (8 3) (3 4) (5 5) (8 9)) file)
(expect #{[61 32] [62 31] [61 30] [60 31]} 
	(into #{} (chr/genCoords [61 31] 1)))
(expect #{[4 3] [7 2] [7 4] [8 3] [5 4] [6 5] [5 2] [6 1]} 
	(into #{} (chr/genCoords [6 3] 2)))
;(expect 3 (into #{} (chr/genCoords [6 3] 3)))

(expect 6 (chr/size [[4 3] [7 2] [7 4]]))

(expect true (chr/coordsinmiddle [3 4][[1 1][1 6][8 3]]))
(expect false (chr/coordsinmiddle [3 4][[8 9][5 5][8 3]]))

(expect nil (chr/inmiddle [1 1] file))
(expect nil (chr/inmiddle [1 6] file))
(expect nil (chr/inmiddle [8 3] file))
(expect true (chr/inmiddle [3 4] file))
(expect true (chr/inmiddle [5 5] file))
(expect nil (chr/inmiddle [8 9] file))

;(expect file (chr/reduceList file))
(expect ' ("ABC" "ABD" "ABE" "ACD" "ACE" "ADE" "BCD" "BCE" "BDE" "CDE") 
	(map #(apply str %) (chr/findallperms ["A" "B" "C" "D" "E"])))

(expect [[3 4][5 5]] (chr/whoIsInMiddle file))
;(expect "" (chr/whoIsInMiddle realFile))
)