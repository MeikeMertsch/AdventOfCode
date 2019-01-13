(ns christmas.chr18.day01-test
  (:require [christmas.chr18.day01 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))
(comment
(def file (slurp "resources/chr18/Input01"))

(expect '(12 -13 17 17 -10 6 13 13 -9 13 -20) (chr/file_as_Ints file))

;;; Part one

(expect 3 (chr/exercise01 "+1\n+1\n+1"))
(expect 0 (chr/exercise01 "+1\n+1\n-2"))
(expect -6 (chr/exercise01 "-1\n-2\n-3"))

;(expect 508 (chr/exercise01 chr/realFile))

;;; Part two

(expect 2 (chr/exercise01b "+1\n-2\n+3\n+1"))
(expect 0 (chr/exercise01b "+1\n-1"))
(expect 10 (chr/exercise01b "+3\n+3\n+4\n-2\n-4"))
(expect 5 (chr/exercise01b "-6\n+3\n+8\n+5\n-6"))
(expect 14 (chr/exercise01b "+7\n+7\n-2\n-7\n-4"))

;(expect 549 (chr/exercise01b chr/realFile))
)