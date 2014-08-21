(ns clj-compress.core
  (:require [clojure.java.io :as io])
  (:import [org.apache.commons.compress.archivers ArchiveInputStream ArchiveStreamFactory]
           [org.apache.commons.compress.archivers.zip ZipArchiveEntry]
           [java.io OutputStream FileOutputStream FileInputStream]))

(defn files->zip [files zipFilePath]
  (let [zipFile (io/file zipFilePath)
      fo (FileOutputStream. zipFile)
      archiveOut (-> (ArchiveStreamFactory.)
                     (.createArchiveOutputStream ArchiveStreamFactory/ZIP fo))]
  (doseq [f files]
    (.putArchiveEntry archiveOut (ZipArchiveEntry. (.getName f)))
    (io/copy (FileInputStream. f) archiveOut)
    (.closeArchiveEntry archiveOut))
  (.close archiveOut)
  (.close fo)
  zipFile))
