(ns christmas.chr15.day17-test
  (:require [christmas.chr15.day17 :as chr]
  		  	[christmas.tools :as t]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(defn parse-file [file]
	(->> (str/split-lines file)
		 (map t/parse-int)
		 (map-indexed vector)))

(def file (parse-file (slurp "resources/christmas/chr15/day17real")))
(def t-file (parse-file "20\n15\n10\n5\n5"))

(expect 4 (chr/day17 t-file 25))
;(expect 1638 (chr/day17 file 150)) ;~ 150 s

(expect 17 (chr/day17b file 150))

(expect 3 (chr/maximum t-file 25))
(expect 2 (chr/minimum t-file 25))

(expect 3 (chr/day17b t-file 25))
)