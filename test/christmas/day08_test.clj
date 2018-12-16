(ns christmas.day08-test
  (:require [christmas.day08 :as chr]
  		  	[expectations :refer :all]
            [christmas.core :refer :all]
            [clojure.string :as str]))
(comment
(defn parse [my_string]
	(Integer/parseInt my_string))

(defn getFile [fileName]
	(-> (slurp fileName)
		(str/split #" ")
		(#(map parse %))))

(def file (getFile "resources/day06-10/Input08"))
(def fileb (getFile "resources/day06-10/Input08b"))  ; I invented that one
(def realFile (getFile "resources/day06-10/Input08real"))


(expect 66 (chr/exercise08b file))
(expect 17 (chr/exercise08b fileb))
;(expect 16653 (chr/exercise08b realFile))

(expect 99 (chr/node-without-kids [0 1 99]))
(expect 33 (chr/node-without-kids [0 3 10 11 12]))
(expect 0 (chr/node-with-kids [1 1 [0 1 99] 2]))
(expect 66 (chr/node-with-kids [2 3 [0 3 10 11 12] [1 1 [0 1 99] 2] 1 1 2]))
(expect 17 (chr/node-with-kids [2 1 [3 3 [0 1 2] [0 3 7 8 9] [1 1 [0 2 6 12] 1] 4 1 2] [1 3 [0 1 17] 1 4 4] 2]))
(expect 17 (chr/node-with-kids [1 3 [0 1 17] 1 4 4]))
(expect 17 (chr/node-without-kids [0 1 17]))
;_________________

(expect 138 (chr/exercise08 file))
(expect 80 (chr/exercise08 fileb))
;(expect 41555 (chr/exercise08 realFile))

(expect [2] (chr/build-tree [] 2))
(expect [2 1 3 3 0] (chr/build-tree [2 1 3 3] 0))
(expect [2 1 3 3 [0 1 2]] (chr/build-tree [2 1 3 3 0 1] 2))
(expect [2 1 3 3 0 1] (chr/build-tree [2 1 3 3 0] 1))
(expect [2 3 [0 3 10 11 12] 1] (chr/build-tree [2 3 [0 3 10 11 12]] 1))
(expect [2 3 [0 3 10 11 12] 1 1 [0 1 99]] (chr/build-tree [2 3 [0 3 10 11 12] 1 1 0 1] 99))

(expect [2 1 3 3 [0 1 2]] (chr/check [2 1 3 3 0 1 2]))
(expect [2 1 3 3 0 1] (chr/check [2 1 3 3 0 1]))

(expect [2 1 3 3 [0 1 2]] (chr/deal-with-0 [2 1 3 3 0 1 2]))
(expect [2 1 3 3 0 3] (chr/deal-with-0 [2 1 3 3 0 3]))

(expect [2 1 3 3 [1 1 [] 2]] (chr/check [2 1 3 3 1 1 [] 2]))

(expect [2 1 3 1 1 []] (chr/check [2 1 3 1 1 []]))
(expect [2 1 [] 3 2 1 [] 1] (chr/check [2 1 [] 3 2 1 [] 1]))
(expect [2 1 3 2 1 1] (chr/check [2 1 3 2 1 1]))
(expect [2 1 [3 1 [] [] [] 2]] (chr/check [2 1 3 1 [] [] [] 2]))

(expect [2 1 [3 3 [0 1 2] [0 3 7 8 9] [1 1 [0 2 6 12] 1] 4 1 2] [1 3 [0 1 17] 1 4 4] 2] (last (reduce chr/build-tree [] fileb)))
)